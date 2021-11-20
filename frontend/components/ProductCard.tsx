import Link from "next/link";
import {Product} from "@types";

interface ProductCardProps {
  product: Product;
}

export default function ProductCard({product}: ProductCardProps) {
  return (
    <div key={product?.id} className="bg-gray-700 rounded-lg p-4">
      <p>{product?.title}</p>
      <p>{product?.price}</p>
      <Link href={`/products/${product?.id}`}>
        <a>Buy</a>
      </Link>
    </div>
  );
}
