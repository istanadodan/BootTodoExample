
const TodoList = ({ todos }) => {
    return (
        <ul className="TodoList">
            {todos.map((todo, index) => (
                <ToDoListItem todo={todo} key={todo.id}/>
            ))}
        </ul>
    )
}

export default TodoList;