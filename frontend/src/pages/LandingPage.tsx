import { Link } from "react-router-dom";

export default function LandingPage() {
  return (
    <>
      <h1 className="text-8xl">Welcome to Mustang</h1>
      <h3 className="text-3xl">A marketplace exclusively for Western students.</h3>
      <Link to="/signup">
        <button>Sign Up</button>
      </Link>
      <Link to="/signin">
        <button>Sign In</button>
      </Link>
    </>
  );
}
