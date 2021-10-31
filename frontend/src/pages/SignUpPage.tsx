import { useState } from "react";

export default function SignUpPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  function onClickSignUp(event: any) {
    event.preventDefault();
    if (!name || !email || !password) {
      alert("All fields are required.");
    } else if (password !== confirmPassword) {
      alert("Passwords do not match.");
    } else {
      alert("Signed Up!");
    }
  }

  return (
    <main className="container mx-auto px-4 flex flex-col items-center justify-center h-screen">
      <h1 className="text-4xl font-medium text-center">Sign Up</h1>
      <form className="flex flex-col items-center">
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={event => setName(event.target.value)}
        />
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
          type="password"
          placeholder="Confirm Password"
          value={confirmPassword}
          onChange={event => setConfirmPassword(event.target.value)}
        />
        <input
          className="my-4 px-4 py-2 bg-purple-700 text-white rounded-lg"
          type="submit"
          onClick={onClickSignUp}
          value="Sign Up"
        />
      </form>
    </main>
  );
}
