import {Router} from 'react-router';

import MainView from './component/MainView';
import Routing from './util/Routing';
import {Provider} from "react-redux";
import AppStore from "./store/AppStore";

const App = () => {
    return <Provider store={AppStore}>
        <Router history={Routing.history}>
            <MainView/>
        </Router>
    </Provider>
};

export default App;
