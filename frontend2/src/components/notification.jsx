import React from 'react'

const style = {
    wrapper: {
        margin: 0,
        padding: 0,
        display: 'flex',
        flecDirection: 'row',
        border: '1px solid grey',
        borderRadius: '5px',
    },
    messageText: {
        color: 'black',
        fontSize: 16,
    }
}

class Notification extends React.Component {
    constructor(props) {
        super(props)
        this.state = {}
    }
    render() {
        return (
            <div style={style.wrapper}>
                <span style={style.messageText}>
                    {this.props.message}
                </span>
            </div>
        );
    }

    componentWillUnmount() {
        console.log('componentWillUnmount')
    }
}

export default Notification