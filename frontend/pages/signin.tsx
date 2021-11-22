import { ChangeEvent } from "react";
import Link from "next/link";
import { useRouter } from "next/router";

export default function SignInPage() {
  const router = useRouter();

  async function signInUser(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!event.target["email"] || !event.target["password"]) {
      alert("Email and password are required.");
    }
    await router.replace("/marketplace");
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h3>Sign In</h3>
      <form onSubmit={signInUser}>
        <label htmlFor="email" className="hidden">Email</label>
        <input type="email" id="email" name="email" autoComplete="email" placeholder="Email" required/>
        <br/>
        <label htmlFor="password" className="hidden">Password</label>
        <input type="password" id="password" name="password"
               autoComplete="current-password" placeholder="Password" required
        />
        <br/>
        <button type="submit">Sign In</button>
      </form>
      <small>Don't have an account? <Link href={"/signup"}><a className="underline">Sign up</a></Link>.</small>
    </div>
  );
}
