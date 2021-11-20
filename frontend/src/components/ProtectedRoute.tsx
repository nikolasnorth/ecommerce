import { useAuth } from "../hooks/AuthProvider";
import { Redirect, Route } from "react-router-dom";
import { FC } from "react";

interface ProtectedRouteProps {
  component: FC;
  path: string;
  exact?: boolean;
}

export default function ProtectedRoute({ component, path, exact }: ProtectedRouteProps) {
  const auth = useAuth();

  if (auth.account) {
    return <Route path={path} exact={!!exact} component={component}/>;
  } else {
    return <Redirect to={"/"}/>;
  }
}
