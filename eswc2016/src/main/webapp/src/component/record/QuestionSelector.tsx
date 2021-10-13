import React, {useEffect} from "react";
import {Question} from "../../model/Record";
import Util, {ThunkDispatch} from "../../util/Util";
import {useDispatch, useSelector} from "react-redux";
import {loadQuestions} from "../../action/AsyncActions";
import AppModel from "../../model/AppModel";
import Select from "react-select";
import {SelectOption} from "../../model/Types";

interface QuestionSelectorProps {
    question: Question | null;
    onSelect: (q: Question | null) => void;
}

const QuestionSelector: React.FC<QuestionSelectorProps> = props => {
    const {question, onSelect} = props;
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        dispatch(loadQuestions());
    }, [dispatch]);
    const questions = useSelector((state: AppModel) => state.questions);
    const options = React.useMemo(() => Util.sanitizeArray(questions).map(q => ({
        label: q.has_data_value,
        value: q.id
    })), [questions]);
    const onOptionSelect = (o: SelectOption | null) => {
        const q = o != null ? Util.sanitizeArray(questions).find(q => q.id === o.value) : null;
        onSelect(q !== undefined ? q : null);
    };
    const value = question ? options.find(q => q.value === question.id) : undefined;

    return <Select options={options} placeholder='Select a question or create one' onChange={onOptionSelect}
                   value={value}/>
};

export default QuestionSelector;
