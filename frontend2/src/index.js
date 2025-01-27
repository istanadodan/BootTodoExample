import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
//import App from './App';
import reportWebVitals from './reportWebVitals';
// import {Book, ConfirmDialog} from './components/Book';
// import Clock from './components/Clock';
import {Comment, CommentList} from './components/comment';
import NotificationList from './components/notificationList';
import Counter from './components/count';
import Accommodate from './customHooks/Accommodate';

const root = ReactDOM.createRoot(document.getElementById('root'));
function tick() {
    const element = (
        <div>
            <h1>Hello, world!</h1>
            <h2>It is {new Date().toLocaleTimeString()}.</h2>
        </div>
    );
    root.render(element);
}
function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}
const comments = [
    {user: 'XXXXX', content: 'This is a comment1'},
    {user: 'XXXXX', content: 'This is a comment2'},
    {user: 'XXXXX', content: 'This is a comment3'}
];
setInterval(() => {
    root.render(
      <React.StrictMode>
        <CommentList comments={comments}/>
        <NotificationList />
        <Counter />
        <Accommodate />
      </React.StrictMode>
    );
}, 1000);

reportWebVitals();
