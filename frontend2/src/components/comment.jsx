import React from "react";
import "./css/comment.css";

const styles = {
  wrapper: {
    margin: 8,
    padding: 8,
    display: "flex",
    flexDirection: "row",
    border: "1px solid #ccc",
    borderRadius: 16,
  },
  user: {
    marginLeft: 8,
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
  },
  commentContent: {
    color: "red",
  },
  imageContainer: {
    marginRight: "10px",
  },
  image: {
    width: "50px",
    height: "50px",
    borderRadius: "50%",
    // marginRight: '10px',
  },
};

const Comment = (props) => {
  return (
    <div className="wrapper">
      <div className="imageContainer">
        <img
          className="image"
          src="https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png"
          style={styles.image}
        />
      </div>
      <div className="user" style={styles.user}>
        <span>
          {props.index} : {props.user}
        </span>
        <span>{props.content}</span>
      </div>
    </div>
  );
};

const CommentList = (props) => {
  return (
    <div className="comment-list">
      {props.comments.map((comment, index) => (
        <Comment key={index} user={comment.user} content={comment.content} />
      ))}
    </div>
  );
};
export { Comment, CommentList };
