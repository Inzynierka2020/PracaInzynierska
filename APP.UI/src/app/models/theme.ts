export interface Theme {
    name: string;
    properties: any;
  }
  
  export const first: Theme = {
    name: "first",
    properties: {
        "--bg-primary": "#47A3FF",
        "--bg-accent": "#FFEE93",
        "--bg-success": "#90FFDC",
        "--bg-danger": "#FF928B",
        "--bg-info": "#7A7978",
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
        "--bg-primary": "red",
        "--bg-accent": "#FFEE93",
        "--bg-success": "#90FFDC",
        "--bg-danger": "#FF928B",
        "--bg-info": "#7A7978",
        "--background": "linear-gradient(to bottom, #FFFFFF 192px, #ECE9E6) fixed",
        "--tx-primary": "white",
        "--group-a": "#9CBFFF",
        "--group-b": "#FFB5A8",
        "--group-c": "#8FF2FF",
        "--group-d": "#FFD375",
        "--group-e": "#82FFC8",
    }
  };