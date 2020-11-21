import { Injectable, EventEmitter } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SnackService } from './snack.service';

@Injectable({
  providedIn: 'root'
})
export class ClockService {

  constructor(private _snackService: SnackService) { }

  public frameEmitter = new BehaviorSubject<any>(0);

  private baudRate = Uint32Array.from([57600]);
  private filters = [{vendorId: 4292, productId: 60000}];
  private connected = false;
  private buffer = "";
  private dataEmitter = new EventEmitter();

  isConnected(): boolean {
    return this.connected;
  }

  public getFrame() {
    return this.frameEmitter.asObservable();
  }

  private parseFrame() {
    if (this.buffer[0] != '$')
      return;

    var frames = this.buffer.split('$');
    this.buffer = "";
    frames.forEach(frame => {
      frame = '$' + frame;
      if (frame.endsWith('\n')) {
        this.frameEmitter.next(frame);
        this.frameEmitter.next(0);
      }
      else
        this.buffer += frame;
    });
  }
  connectedBefore = false;
  public connectDevice() {
    if (this.connected)
      return;

    var device;
    var usbAPI = window.navigator['usb'];

    usbAPI.requestDevice({ filters: this.filters })
      .then(usbDevice => {
        device = usbDevice;
        console.log("Product name: " + usbDevice.productName);
        this.connected = true;
        if (!this.connectedBefore)
          this.dataEmitter.subscribe(result => {
            this.buffer += result;
            this.parseFrame()
          });
        return device.open()
      })
      .then(() => device.selectConfiguration(1))
      .then(() => device.claimInterface(0))
      .then(async () => await device.controlTransferOut({
        requestType: 'vendor',
        recipient: 'interface',
        request: 0x1E,
        value: 0,
        index: 0
      }, this.baudRate))
      .then(() => {
        this.listenForData(device, this.dataEmitter);
      })
      .catch(error => {
        console.log(error);
      });

    usbAPI.addEventListener('disconnect', event => {
      this.connected = false;
      this.connectedBefore = true;
      this._snackService.open('USB DEVICE DISCONNECTED')
    });

    usbAPI.addEventListener('connect', event => {
      this._snackService.openForAction('USB DEVICE IS VISIBLE AGAIN. CLICK OK TO CONNECT AGAIN', this)
    });
  }

  private listenForData(device, dataEmitter) {
    var msgBuffer = "";
    setInterval(() => {
      device.transferIn(1, 6400)
        .then(result => {
          let decoder = new TextDecoder();
          let byte = decoder.decode(result.data)
          msgBuffer += byte;
          dataEmitter.emit(byte);
        })
        .catch(alert => {
        })
    }, 50);
  };
}
