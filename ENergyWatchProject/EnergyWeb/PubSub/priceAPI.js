/**
 * @fileoverview This file contains the function to get data from the API and publish it to the broker
 */
//imports the broker instance from main.js
import brokerInstance from "./main";

//function to get data from the API and publish it to the broker
//uses async/await to get the data from the API
//uses the broker instance to publish the data to the broker
async function getData() {
    const url = 'prices.json'; //url to the API
    const response = await fetch(url); 
    const data = await response.json();
    console.log(data);
    brokerInstance.publish('data', data);
}
  
