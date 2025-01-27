import React from 'react'
import Notification from './notification'

const reserveNotifications = [
    { id: 1, message: 'This is a notification1' },
    { id: 2, message: 'This is a notification2' },
    { id: 3, message: 'This is a notification3' },
]

var timer;

class NotificationList extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            notifications: [],
        }
    }
    componentDidMount() {
        const { notifications } = this.state;

        timer = setInterval(() => {
            if (notifications.length < reserveNotifications.length) {
                const index = notifications.length;
                notifications.push(reserveNotifications[index]);

                this.setState({
                    notifications: notifications
                })
            } else {
                clearInterval(timer);

                this.setState({
                    notifications: []
                })
            }
        }, 3000);
    }
    componentWillUnmount() {
        if (timer) {
            clearInterval(timer)
        }
        console.log('componentWillUnmount');
    }
    render() {
        return (
            <div>
                {this.state.notifications.map((notification) => (
                    <Notification key={notification.id} message={notification.message} />
                ))}
            </div>
        );
    }
}

export default NotificationList;