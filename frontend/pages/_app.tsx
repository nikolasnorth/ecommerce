import 'tailwindcss/tailwind.css';
import {AppProps} from "next/app";
import Head from "next/head";

export default function MyApp({Component, pageProps}: AppProps) {
  return (
    <>
      <Head>
        <title>Mustang</title>
      </Head>
      <div className="bg-gray-900 h-screen text-white">
        <Component {...pageProps} />
      </div>
    </>
  );
}
