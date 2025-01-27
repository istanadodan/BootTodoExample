import React, { Suspense, useEffect } from 'react'
import { TodoList} from './components/TodoList'
import './scss/style.scss'

// Containers
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'))

// Pages
const Login = React.lazy(() => import('./views/pages/login/Login'))
const Register = React.lazy(() => import('./views/pages/register/Register'))
const Page404 = React.lazy(() => import('./views/pages/page404/Page404'))
const Page500 = React.lazy(() => import('./views/pages/page500/Page500'))

const App = () => {
  const [todos, setTodos] = useState([
    {
      id:1,
      text:"Todo 1",
      checked: true,
    },
    {
      id:2,
      text:"Todo 2",
      checked: true,
    },
    {
      id:3,
      text:"Todo 3",
      checked: false,
    },
  ]);

  return (
    <TodoList todos={todos} />    
  )
}

export default App
