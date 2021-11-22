import { ChangeEvent } from "react";

export default function SignInPage() {

  async function signInUser(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!event.target["email"] || !event.target["password"]) {
      alert("Email and password are required.");
    }
  }

  return (
    <div className="min-h-screen flex flex-col">
      <h3>Sign in</h3>
      <form onSubmit={signInUser}>
        <label htmlFor="email" className="hidden">Email</label>
        <input type="email" id="email" name="email" autoComplete="email" placeholder="Email" required/>
        <br/>
        <label htmlFor="password" className="hidden">Password</label>
        <input type="password" id="password" name="password"
               autoComplete="current-password" placeholder="Password" required
        />
        <br/>
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}
