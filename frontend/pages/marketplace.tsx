import {GetServerSidePropsResult} from "next";
import {Product} from "@types";

interface MarketplaceProps {
  products: Product[];
}

export async function getServerSideProps(): Promise<GetServerSidePropsResult<MarketplaceProps>> {
  const res = await fetch("https://fakestoreapi.com/products");
  const data = await res.json();
  return {
    props: {
      products: data,
    }
  };
}

export default function Marketplace({products}: MarketplaceProps) {
  return (
    <>
      <header className="mb-12">
        <h3 className="text-3xl font-semibold">Mustang</h3>
      </header>
      <div className="grid gap-4 grid-cols-3 justify-items-stretch min-h-screen">
        {products?.map(product => {
          return (
            <div key={product.id} className="bg-gray-700 rounded-lg p-4">
              <p>{product.title}</p>
              <p>{product.price}</p>
            </div>
          );
        })}
      </div>
    </>
  );
}
