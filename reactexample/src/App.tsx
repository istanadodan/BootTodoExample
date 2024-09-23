// import logo from "./logo.svg";
import "./App.css";
import ApiComponent from "components/ApiComponent";
import ApiComponentList from "components/ApiComponentList";

const App: React.FC = () => {
  return (
    <div className="App">
      <ApiComponent />
      <ApiComponentList />
    </div>
  );
};

export default App;
