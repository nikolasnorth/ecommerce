import { createContext, ReactNode, useContext, useState } from "react";
import { Account } from "../types";
import { useHistory } from "react-router-dom";

interface AuthContext {
  account: Account | null;
  signUp: (_email: string, _name: string, _password: string) => Promise<void>;
  signIn: (_email: string, _password: string) => Promise<void>;
  signOut: () => Promise<void>;
}

const AuthContext = createContext<AuthContext | null>(null);

interface AuthProviderProps {
  children: ReactNode;
  cachedAccount: Account | null;
}

export default function AuthProvider({ children, cachedAccount }: AuthProviderProps) {
  const history = useHistory();
  const [account, setAccount] = useState<Account | null>(cachedAccount);

  async function signUp(email: string, name: string, password: string) {
    const fakeAccount: Account = {
      id: 1,
      email: email,
      name: name,
      password: password,
    };
    localStorage.setItem("account", JSON.stringify(fakeAccount));
    setAccount(fakeAccount);
    history.push("/market");
  }

  async function signIn(email: string, password: string) {
    const fakeAccount: Account = {
      id: 1,
      name: "Nikolas",
      email: email,
      password: password,
    };
    localStorage.setItem("account", JSON.stringify(fakeAccount));
    setAccount(fakeAccount);
    history.push("/market");
  }

  async function signOut() {
    localStorage.removeItem("account");
    setAccount(null);
    history.push("/");
  }

  const value = { account, signUp, signIn, signOut };
  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within AuthProvider.");
  }
  return context;
}
