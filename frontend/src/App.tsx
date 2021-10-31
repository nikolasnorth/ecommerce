import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import MarketPlacePage from "./pages/MarketPlacePage";
import PostProductPage from "./pages/PostProductPage";
import AccountPage from "./pages/AccountPage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact={true} path={"/"} component={LandingPage}/>
        <Route path={"/signin"} component={SignInPage}/>
        <Route path={"/signup"} component={SignUpPage}/>
        <Route path={"/market"} component={MarketPlacePage}/>
        <Route path={"/post"} component={PostProductPage}/>
        <Route path={"/account"} component={AccountPage}/>
        <Route path={"/"} component={NotFoundPage}/>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
