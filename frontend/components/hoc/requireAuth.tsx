import { GetServerSideProps, GetServerSidePropsContext } from "next";
import cookie from "cookie";


export default function requireAuth(getServerSideProps: GetServerSideProps) {
  return async (context: GetServerSidePropsContext) => {
    const { req } = context;

    if (req.headers.cookie) {
      const cookies = cookie.parse(req.headers.cookie)
      console.log(cookies);
    } else {
      console.log("No cookies");
    }

    return await getServerSideProps(context);
  };
}
