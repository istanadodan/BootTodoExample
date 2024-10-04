import { ApiResponse } from "../model/ApiRes";
import "./App.css";

interface Props {
  apiReslst: ApiResponse[];
}

function Res(props: Props) {
  return (
    <div>
      <table className="table">
        <thead>
          <tr>
            <th>User ID</th>
            <th>ID</th>
            <th>Title</th>
            <th>Body</th>
          </tr>
        </thead>
        <tbody>
          {props.apiReslst.map((d) => (
            <tr key={d.userId}>
              <td>{d.userId}</td>
              <td>{d.id}</td>
              <td>{JSON.stringify(d.title,null,2)}</td>
              <td>{d.body}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Res;
