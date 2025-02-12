import React, { useState } from "react";

class MyButton extends React.Component {
  handleClick = () => {
    // this.props.onClickFunction(this.props.incrementValue);
    console.log("this is:", this);
  };

  handleClick2() {
    console.log("this2 is:", this);
  }

  render() {
    // return <button onClick={this.handleClick}>클릭</button>;
    return <button onClick={() => this.handleClick2()}>클릭2</button>;
  }
}

function MyButton2(props) {
  const handleDelete = (id, event) => {
    console.log(id, event.target);
  };

  return <button onClick={(event) => handleDelete(1, event)}>삭제하기</button>;
}

function ConfirmButton(props) {
  const [isConfirm, setIsConfirm] = React.useState(false);

  const toggleConfirm = () => {
    setIsConfirm((preState) => !preState);
  };

  return (
    <button onClick={toggleConfirm} disabled={isConfirm}>
      {isConfirm ? "확인됨" : "확인하기"}
    </button>
  );
}

function UserGreeting(props) {
  return <h1>다시 오셨군요!</h1>;
}
function GuestGreeting(props) {
  return <h1>회원가입을 해 주세요</h1>;
}

function LoginButton(props) {
  return <button onClick={props.onClick}>로그인2 {props.children}</button>;
}

function LogoutButton(props) {
  return <button onClick={props.onClick}>로그아웃</button>;
}

function Greeting(props) {
  const isLoggedIn = props.isLoggedIn;
  if (isLoggedIn) {
    return <UserGreeting />;
  }
  return <GuestGreeting />;
}

function LoginControl(props) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLoginClick = () => {
    setIsLoggedIn(true);
  };

  const handleLogoutClick = () => {
    setIsLoggedIn(false);
  };

  let button = isLoggedIn ? (
    <LogoutButton onClick={handleLogoutClick}>로그아웃</LogoutButton>
  ) : (
    <LoginButton onClick={handleLoginClick}>로그인1</LoginButton>
  );

  return (
    <div>
      <Greeting isLoggedIn={isLoggedIn} />
      {button}
    </div>
  );
}

export { MyButton2, MyButton, ConfirmButton, LoginControl };
