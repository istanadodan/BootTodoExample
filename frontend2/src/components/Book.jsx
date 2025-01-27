import React from 'react';

function Book(props) {
    return (
        <div className="book">
            {/* <img src={props.book.imageLinks.thumbnail} alt={props.book.title} /> */}
            <h3>{props.book}</h3>
            <p>{props.pages}</p>
            {/* <p>{props.book.description}</p> */}
            {/* <a href={props.book.previewLink}>Preview</a> */}
        </div>
    );
}

function Button(props) {
    return (
        <button className={'bg-${props.color}'}>
            <b>
                {props.children}
            </b>
        </button>
    );
}

function ConfirmDialog(props) {
    return (
        <div className="confirm-dialog">
            <p>내용을 확인하셨으면 확인 버튼을 눌러주세요</p>
            <Button color="green">확인</Button>
        </div>
    );
}
// ConfirmDialog와 Book를 export 하기

export { ConfirmDialog, Book };