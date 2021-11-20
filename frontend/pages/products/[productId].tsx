import {Product} from "@types";
import {GetServerSidePropsContext, GetServerSidePropsResult} from "next";
import {ParsedUrlQuery} from "querystring";
import Link from "next/link";

interface ProductPageProps {
  product: Product;
}

interface ProductPagePropsContext extends ParsedUrlQuery {
  productId: string;
}

export async function getServerSideProps(context: GetServerSidePropsContext<ProductPagePropsContext>)
  : Promise<GetServerSidePropsResult<ProductPageProps>> {
  const {productId} = context.params;
  const res = await fetch(`https://fakestoreapi.com/products/${productId}`);
  const data = await res.json();
  return {
    props: {
      product: data
    }
  };
}

export default function ProductPage({product}: ProductPageProps) {
  return (
    <div className="min-h-screen">
      <h3>{product?.title}</h3>
      <p>{product?.price}</p>
      <Link href={"/marketplace"}>
        <a>Buy</a>
      </Link>
    </div>
  );
}
