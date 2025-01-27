import { useState } from "react";
import { useUserStatus } from "./customHooks/customHooks";

const userList = [
  { id: 1, name: "Inje" },
  { id: 2, name: "Mike" },
  { id: 3, name: "Steve" },
];

function ChatUserSelector(props) {
  const [userId, setUserId] = useState(1);
  const isUserOnline = useUserStatus(userId);
  return (
    <>
      <Circle color={isUserOnline ? "green" : "red"} />
      <select
        value={userId}
        onChange={(event) => setUserId(event.target.value)}
      >
        {userList.map((user) => (
          <option key={user.id} value={user.id}>
            {user.name}
          </option>
        ))}
      </select>
    </>
  );
}
