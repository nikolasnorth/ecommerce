import { useState } from "react";
import { useAuth } from "../hooks/AuthProvider";

export default function SignInPage() {
  const auth = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function onClickSignIn(event: any) {
    try {
      event.preventDefault();
      if (!email || !password) {
        alert("All fields are required.");
      } else {
        await auth.signIn(email, password);
      }
    } catch (e) {
      console.error(e);
      alert("Something went wrong. Please try again later.");
    }
  }

  return (
    <main className="container mx-auto px-4 flex flex-col items-center justify-center h-screen">
      <h1 className="text-4xl font-medium text-center">Sign In</h1>
      <form className="flex flex-col items-center">
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={event => setEmail(event.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={event => setPassword(event.target.value)}
        />
        <input
          className="my-4 px-4 py-2 bg-purple-700 text-white rounded-lg"
          type="submit"
          onClick={onClickSignIn}
          value="Sign In"
        />
      </form>
    </main>
  );
}
