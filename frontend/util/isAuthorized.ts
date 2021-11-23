import { GetServerSidePropsContext } from "next";

export default function isAuthorized(context: GetServerSidePropsContext) {
  return !!context.req.cookies["access-token"];
}
