import { ChangeEvent } from "react";
import Link from "next/link";

export default function SignUpPage() {

  async function signUpUser(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!event.target["email"] || !event.target["password"] || !event.target["confirmPassword"]) {
      alert("All fields are required.");
    }
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h3>Sign Up</h3>
      <form onSubmit={signUpUser}>
        <label htmlFor="email" className="hidden">Email</label>
        <input type="email" id="email" name="email" autoComplete="email" placeholder="Email" required/>
        <br/>
        <label htmlFor="password" className="hidden">Password</label>
        <input type="password" id="password" name="password"
               autoComplete="new-password" placeholder="Password" required
        />
        <br/>
        <label htmlFor="confirm-password" className="hidden">Confirm Password</label>
        <input type="password" id="confirm-password" name="confirmPassword"
               autoComplete="new-password" placeholder="Confirm Password" required
        />
        <br/>
        <button type="submit">Sign Up</button>
      </form>
      <small>Already have an account? <Link href={"/signin"}><a className="underline">Sign in</a></Link>.</small>
    </div>
  );
}
