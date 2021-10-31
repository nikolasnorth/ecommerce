import { Link } from "react-router-dom";

export default function LandingPage() {
  return (
    <main className="container mx-auto px-4 flex flex-col items-center justify-center h-screen">
      <h1 className="text-8xl">Welcome to <span className="text-purple-700">Mustang</span></h1>
      <h3 className="text-3xl text-gray-700 my-4">A marketplace exclusively for Western students.</h3>
      <div className="flex space-x-4 my-4">
        <Link to="/signup">
          <button className="px-4 py-2 bg-purple-700 text-white rounded-lg">Sign Up</button>
        </Link>
        <Link to="/signin">
          <button className="px-4 py-2 bg-gray-700 text-white rounded-lg">Sign In</button>
        </Link>
      </div>
    </main>
  );
}
