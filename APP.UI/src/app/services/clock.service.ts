import { Injectable, EventEmitter } from '@angular/core';
import { parse } from 'querystring';
import { Observable, Subject, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClockService {

  constructor() { }

  public frameEmitter = new BehaviorSubject<any>(0);

  private baudRate = Uint32Array.from([57600]);
  private filters = [];
  private connected = false;
  private buffer = "";
  private dataEmitter = new EventEmitter();


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
      if (frame.endsWith('\n')){
        this.frameEmitter.next(frame);
        this.frameEmitter.next(0);
      }
      else
        this.buffer += frame;
    });
  }

  public connectDevice() {
    if (this.connected)
      return;

    this.dataEmitter.subscribe(result => {
      this.buffer += result;
      this.parseFrame()
    });

    var device;
    var usbAPI = window.navigator['usb'];
    usbAPI.requestDevice({ filters: this.filters })
      .then(usbDevice => {
        device = usbDevice;
        console.log("Product name: " + usbDevice.productName);
        this.connected = true;
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
      .catch(error => { console.log(error); });

  }

  private listenForData(device, dataEmitter) {
    var msgBuffer = "";
    setInterval(() => {
      device.transferIn(1, 6400)
        .then(result => {
          let decoder = new TextDecoder();
          let byte = decoder.decode(result.data)
          msgBuffer += byte;
          // if(byte == '\n'){
          //   console.log(msgBuffer);
          //   emit.emit(msgBuffer); 
          //   msgBuffer = "";  
          // }
          dataEmitter.emit(byte);
        })
        .catch(alert => {
        })
      // .then(function () {
      //    listen(device, msgBuffer);
      // });
    }, 50);
  };

}
