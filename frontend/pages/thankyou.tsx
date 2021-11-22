import { GetServerSidePropsContext, GetServerSidePropsResult } from "next";
import isAuthorized from "@util/isAuthorized";

export async function getServerSideProps(context: GetServerSidePropsContext)
  : Promise<GetServerSidePropsResult<unknown>> {
  if (!isAuthorized(context)) {
    return {
      redirect: {
        destination: "/signin",
        permanent: false,
      }
    };
  }
  return {
    props: {}
  };
}

export default function PurchaseConfirmationPage() {
  return (
    <div className="min-h-screen">
      <h1>Thank you for your purchase!</h1>
    </div>
  );
}
