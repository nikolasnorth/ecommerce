import { ChangeEvent, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import { GetServerSideProps } from "next";
import requireAuth from "@components/hoc/requireAuth";

export const getServerSideProps: GetServerSideProps = requireAuth(async () => {
  console.log("Hello from server side props");
  return {
    props: {}
  };
});

export default function SignUpPage() {
  const router = useRouter();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  async function signUpUser(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!name || !email || !password || !password || !confirmPassword) {
      alert("All fields are required.");
    } else if (password != confirmPassword) {
      alert("Passwords did not match.");
    }
    const res = await fetch("http://localhost:8083/api/v1/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({
        email: email,
        name: name,
        password: password,
      })
    });
    if (!res.ok) {
      alert("Error occurred while signing up.");
    }
    const data = await res.json();
    console.log(data);

    await router.replace("/marketplace");
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h3>Sign Up</h3>
      <form onSubmit={signUpUser}>
        <label htmlFor="name" className="hidden">Email</label>
        <input type="text" id="name" name="name"
               autoComplete="given-name" placeholder="Name" required onChange={event => setName(event.target.value)}
        />
        <br/>
        <label htmlFor="email" className="hidden">Email</label>
        <input type="email" id="email" name="email"
               autoComplete="email" placeholder="Email" required onChange={event => setEmail(event.target.value)}
        />
        <br/>
        <label htmlFor="password" className="hidden">Password</label>
        <input type="password" id="password" name="password" autoComplete="new-password"
               placeholder="Password" required onChange={event => setPassword(event.target.value)}
        />
        <br/>
        <label htmlFor="confirm-password" className="hidden">Confirm Password</label>
        <input type="password" id="confirm-password" name="confirmPassword" autoComplete="new-password"
               placeholder="Confirm Password" required onChange={event => setConfirmPassword(event.target.value)}
        />
        <br/>
        <button type="submit">Sign Up</button>
      </form>
      <small>Already have an account? <Link href={"/signin"}><a className="underline">Sign in</a></Link>.</small>
    </div>
  );
}
