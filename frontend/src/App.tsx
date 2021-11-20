import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import MarketPlacePage from "./pages/MarketPlacePage";
import PostProductPage from "./pages/PostProductPage";
import AccountPage from "./pages/AccountPage";
import { useEffect, useState } from "react";
import { Account } from "./types";
import ProtectedRoute from "./components/ProtectedRoute";
import AuthProvider from "./hooks/AuthProvider";

function App() {
  const [isLoading, setLoading] = useState(true);
  let account: Account | null = null;

  useEffect(() => {
    // When application is first launched, check to see if the user is already logged in by
    // querying localStorage for a cached account.
    try {
      const accountString = localStorage.getItem("account");
      if (accountString) {
        account = JSON.parse(accountString) as Account;
      }
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  }, []);

  if (isLoading) {
    return (
      <main className="flex h-screen justify-center items-center">
        <p>Loading...</p>
      </main>
    );
  }
  return (
    <BrowserRouter>
      <Switch>
        <AuthProvider cachedAccount={account}>
          <Route exact path={"/"} component={LandingPage}/>
          <Route path={"/signin"} component={SignInPage}/>
          <Route path={"/signup"} component={SignUpPage}/>
          <ProtectedRoute path={"/market"} component={MarketPlacePage}/>
          <ProtectedRoute path={"/post"} component={PostProductPage}/>
          <ProtectedRoute path={"/account"} component={AccountPage}/>
        </AuthProvider>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
