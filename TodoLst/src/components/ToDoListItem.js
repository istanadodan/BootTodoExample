import { CNav } from "@coreui/react";
import { MdRemoveCircleOutline } from 'react-icons/md';

const toDoListItem = ({todo}) => {
    const {id,text,checked} = todo;
    return (
        <li className="TodoListItem">
            <div className={CNav('checkbox', {checked})}>
                {checked ? <MdCheckBox/> : <MdCheckBoxOutlineBlank/>}
                <div className="text">{text}</div>
            </div>
            <div className="edit">
                <MdModeEditOutline/>
            </div>
            <div className="remove">
                <MdRemoveCircleOutline/>
            </div>
        </li>
    )
}