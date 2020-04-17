export interface Theme {
    name: string;
    properties: any;
  }
  
  export const first: Theme = {
    name: "first",
    properties: {
        "--th-primary-dark": "#000000",
        "--th-primary": "#202223",
        "--th-primary-light": "#6d6d6d",
        "--th-accent-dark": "rgb(40, 105, 165)",
        "--th-accent": "#4499E9",
        "--th-accent-light": "rgb(114, 184, 250)",
        "--th-disabled": "#20222380",
        "--th-background": "linear-gradient(to bottom, #FFFFFF 192px, #ECE9E6) fixed",
        "--text-primary": "white",
        "--text-accent": "white",
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
        "--th-accent-dark": "#000000",
        "--th-accent": "#202223",
        "--th-accent-light": "#6d6d6d",
        "--th-primary-dark": "rgb(5, 64, 119)",
        "--th-primary": "#4499E9",
        "--th-primary-light": "rgb(114, 184, 250)",
        "--th-disabled": "#20222380",
        "--th-background": "linear-gradient(to bottom, #FFFFFF 192px, #ECE9E6) fixed",
        "--text-primary": "white",
        "--text-accent": "white",
        "--group-a": "#9CBFFF",
        "--group-b": "#FFB5A8",
        "--group-c": "#8FF2FF",
        "--group-d": "#FFD375",
        "--group-e": "#82FFC8",
    }
  };