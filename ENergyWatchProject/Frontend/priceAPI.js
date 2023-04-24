import brokerInstance from "./main";

//function to get data from the API and publish it to the broker
async function getData() {
    const response = await fetch('mock.json'); //change this to the real url
    const data = await response.json();
    brokerInstance.publish('data', data);
}
  
