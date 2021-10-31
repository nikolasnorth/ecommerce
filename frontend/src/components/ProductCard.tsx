import { Product } from "../types";

interface ProductCardProps {
  product: Product;
}

export default function ProductCard({ product }: ProductCardProps) {
  return (
    <div>
      <h3 className="text-center font-medium">{product.name}</h3>
      <p>${product.price}</p>
      <p>Sold by: {product.soldBy} ({product.rating}%)</p>
    </div>
  );
}
