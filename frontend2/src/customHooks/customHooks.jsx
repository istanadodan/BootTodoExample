import { useState, useEffect } from "react";

// 사용자가 online인지 상태를 구독.
// 사용자훅인경우 반드시 use로 시작하도록 한다.
function useUserStatus(userId) {
  const { isOnline, setIsOnline } = useState(null);
  useEffect(() => {
    function handleStatusChange(status) {
      setIsOnline(status.isOnline);
    }

    ServerAPI.subscribeUserStatus(userId, handleStatusChange);
    return () => {
      ServerAPI.unsubscribeUserStatus(userId, handleStatusChange);
    };
  });

  return isOnline;
}

function UserListItem(props) {
  //내부상태변수는 독립적적
  const isOnline = useUserStatus(props.user.id);

  return (
    <li style={{ color: isOnline ? "green" : "black" }}>{props.user.name}</li>
  );
}

export { useUserStatus, UserListItem };
