import Link from "next/link";

export default function Home() {
  return (
    <main className="flex flex-col justify-center items-center h-screen">
      <h1 className="text-7xl font-medium mb-12">Welcome to <b>Mustang</b></h1>
      <Link href={"marketplace"}>
        <a className="bg-blue-600 px-8 py-2 rounded-md">Get started</a>
      </Link>
    </main>
  );
}
