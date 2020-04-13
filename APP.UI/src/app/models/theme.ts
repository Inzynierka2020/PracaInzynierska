export interface Theme {
    name: string;
    properties: any;
  }
  
  export const first: Theme = {
    name: "first",
    properties: {
        "--bg-primary": "#202223",
        "--bg-accent": "#4499E9",
        "--bg-success": "#80ffc0",
        "--bg-danger": "#FF928B",
        "--background": "linear-gradient(to bottom, #FFFFFF 192px, #ECE9E6) fixed",
        "--tx-primary": "white",
        "--group-a": "#9CBFFF",
        "--group-b": "#FFB5A8",
        "--group-c": "#8FF2FF",
        "--group-d": "#FFD375",
        "--group-e": "#82FFC8",
    }
  };
  
  export const second: Theme = {
    name: "second",
    properties: {
        "--bg-primary": "#4499E9",
        "--bg-accent": "#202223",
        "--bg-success": "#47494b",
        "--bg-danger": "#FF928B",
        "--background": "linear-gradient(to bottom, #FFFFFF 192px, #ECE9E6) fixed",
        "--tx-primary": "white",
        "--group-a": "#9CBFFF",
        "--group-b": "#FFB5A8",
        "--group-c": "#8FF2FF",
        "--group-d": "#FFD375",
        "--group-e": "#82FFC8",
    }
  };