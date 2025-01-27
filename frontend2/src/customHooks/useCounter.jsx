import React, { useState } from "react";

function useCounter(initialValue) {
  const [count, setCount] = useState(initialValue);

  const increaseCount = () => {
    setCount(count + 1);
  };
  const decreaseCount = () => {
    setCount(Math.max(count - 1, 0));
  };
  return [count, increaseCount, decreaseCount];
}

export default useCounter;
