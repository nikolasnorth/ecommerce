import 'tailwindcss/tailwind.css';
import {AppProps} from "next/app";
import Head from "next/head";

export default function MyApp({Component, pageProps}: AppProps) {
  return (
    <>
      <Head>
        <title>Mustang</title>
      </Head>
      <div className="bg-gray-100 h-full p-4">
        <Component {...pageProps} />
      </div>
    </>
  );
}
