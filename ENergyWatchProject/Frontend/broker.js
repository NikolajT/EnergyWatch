export default class Broker {
    constructor() {
        this._subscribers = [];
    }

    //sign up for a topic
    subscribe(subscriber) {
        this.subscribers = [...this._subscribers, subscriber]
    }

    //unsign a topic
    unsubscribe(subscriber) {
        this._subscribers = this._subscribers.filter(s => s !== subscriber);
    }

    //publish a topic
    publish(payload) {
        this._subscribers.forEach(subscriber => subscriber(payload));
    }
}