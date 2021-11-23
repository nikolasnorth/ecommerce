import Link from "next/link";

export default function Home() {
  return (
    <main className="flex flex-col justify-center items-center min-h-screen">
      <h1 className="text-7xl font-medium mb-12">Welcome to <b>Mustang</b></h1>
      <Link href={"signup"}>
        <a className="bg-purple-700 px-8 py-2 rounded-md text-white font-semibold">Get started</a>
      </Link>
    </main>
  );
}
