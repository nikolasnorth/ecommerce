import 'tailwindcss/tailwind.css';
import {AppProps} from "next/app";
import Head from "next/head";

export default function MyApp({Component, pageProps}: AppProps) {
  return (
    <>
      <Head>
        <title>Mustang</title>
      </Head>
      <div className="bg-gray-900 h-full text-white p-4">
        <Component {...pageProps} />
      </div>
    </>
  );
}
