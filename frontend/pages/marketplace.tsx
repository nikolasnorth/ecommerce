import { GetServerSidePropsContext, GetServerSidePropsResult } from "next";
import { Product } from "@types";
import ProductCard from "@components/ProductCard";
import isAuthorized from "@util/isAuthorized";

interface MarketplacePageProps {
  products: Product[];
}

export async function getServerSideProps(context: GetServerSidePropsContext)
  : Promise<GetServerSidePropsResult<MarketplacePageProps>> {
  if (!isAuthorized(context)) {
    return {
      redirect: {
        destination: "/signin",
        permanent: false,
      }
    };
  }
  const productsRes = await fetch("https://fakestoreapi.com/products");
  const productData = await productsRes.json();
  return {
    props: {
      products: productData,
    }
  };
}

export default function Marketplace({ products }: MarketplacePageProps) {
  return (
    <>
      <header className="mb-12">
        <h3 className="text-3xl font-semibold">Mustang</h3>
      </header>
      <div className="grid gap-4 grid-cols-3 justify-items-stretch min-h-screen">
        {products?.map(product => <ProductCard product={product}/>)}
      </div>
    </>
  );
}
