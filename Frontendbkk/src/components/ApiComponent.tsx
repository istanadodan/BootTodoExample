import React, { useState, useEffect } from "react";
import axios from "axios";
import { ApiResponse } from "models/ApiRes";
import Res from "components/ApiRes";

function ApiComponent() {
  const [data, setData] = useState<ApiResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  // const url = "https://jsonplaceholder.typicode.com/posts";
  // const url = "http://localhost/backend/demo/";
  const url = `${process.env.REACT_APP_API_URL}/`;
  // const url = "/demo/";
  // const header = { "withCredentials": false };
  axios.defaults.withCredentials = false;
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await axios.get(url);
      setData([
        { userId: 1, id: 1, title: response.data, body: "" + response.status },
      ]);
      setLoading(false);
    } catch (err) {
      setError(String(err));
      setLoading(false);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Data from API!!!!</h1>
      <Res apiReslst={data} />
      {/* <pre>{JSON.stringify(data, null, 2)}</pre> */}
    </div>
  );
}

export default ApiComponent;
