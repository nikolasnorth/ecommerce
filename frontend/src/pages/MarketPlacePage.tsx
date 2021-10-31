import { Link } from "react-router-dom";
import { FormEvent, useState } from "react";
import ProductCard from "../components/ProductCard";
import { Product } from "../types";

export default function MarketPlacePage() {
  const [searchQuery, setSearchQuery] = useState("");
  const [products, setProducts] = useState(new Array<Product>());
  const fakeProducts = new Array<Product>(
    { id: 1, name: "Product One", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 2, name: "Product Two", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 3, name: "Product Three", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 4, name: "Product Four", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 5, name: "Product Five", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 6, name: "Product Six", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 7, name: "Product Seven", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 8, name: "Product Eight", price: 100.00, soldBy: "Nikolas", rating: 80 },
    { id: 9, name: "Product Nine", price: 100.00, soldBy: "Nikolas", rating: 80 },
  );

  function onSearchSubmission(event: FormEvent<HTMLInputElement>) {
    event.preventDefault();
    if (!searchQuery) return;
    setProducts(fakeProducts);
    setSearchQuery("");
  }

  return (
    <>
      <header className="flex justify-between">
        <h1 className="text-3xl font-medium">Marketplace</h1>
        <nav className="flex space-x-4">
          <Link to="/post">Post Product</Link>
          <Link to="/account">My Account</Link>
        </nav>
      </header>
      <main>
        <form className="flex justify-center">
          <input
            type="text"
            placeholder="Search for product"
            value={searchQuery}
            onChange={event => setSearchQuery(event.target.value)}
          />
          <input className="hidden" type="submit" onClick={onSearchSubmission}/>
        </form>
        <section className="grid gap-4 grid-cols-3 justify-items-center">
          {products?.map(product => (<ProductCard product={product} key={product.id}/>))}
        </section>
      </main>
    </>
  );
}
