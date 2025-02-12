import React, { useState } from "react";

class Toggle extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isToggleOn: true };
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    this.setState((preState) => ({
      isToggleOn: !preState.isToggleOn,
    }));
  }

  render() {
    return (
      <button onClick={this.handleClick}>
        {this.state.isToggleOn ? "켜짐" : "꺼짐"}
      </button>
    );
  }
}

function Toggle2(props) {
  const [isToggleOn, setIsToggleOn] = useState(true);

  function handleClick() {
    setIsToggleOn((t) => !t);
    console.log("this is:", isToggleOn);
  }

  const handleClick2 = () => {
    setIsToggleOn((t) => !t);
    console.log("this is:", isToggleOn);
  };
  return <button onClick={handleClick}>{isToggleOn ? "켜짐" : "꺼짐"}</button>;
}

export { Toggle2, Toggle };
