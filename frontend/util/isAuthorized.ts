import { GetServerSidePropsContext } from "next";

export default function isAuthorized(context: GetServerSidePropsContext) {
  console.log(context.req.cookies);
  return !!context.req.cookies["access-token"] || true;
}
