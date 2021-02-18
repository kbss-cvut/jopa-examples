import MainView from "./component/MainView";
import {Container} from "react-bootstrap";

function App() {
    return <>
        <header className="ml-2 mr-2 mt-2">
            <h1>Example04 - JOPA + Spring</h1>
        </header>
        <Container id="content-container" fluid={true} className="mt-5 mb-5 flex-grow-1">
            <MainView/>
        </Container>
    </>;
}

export default App;
