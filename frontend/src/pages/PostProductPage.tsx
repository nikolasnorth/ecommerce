import { useState } from "react";

export default function PostProductPage() {
  const [price, setPrice] = useState<number>();
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  function onSubmitPostProduct(event: any) {
    event.preventDefault();
    if (!price || !name || !description) {
      alert("All fields are required.");
    } else {
      alert("Product posted.");
    }
  }

  return (
    <main className="container mx-auto px-4 flex flex-col items-center justify-center h-screen">
      <h1 className="text-4xl font-medium text-center">Post Product</h1>
      <form className="flex flex-col items-center">
        <input
          type="number"
          placeholder="Price"
          value={price}
          onChange={event => setPrice(Number(event.target.value))}
        />
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={event => setName(event.target.value)}
        />
        <textarea
          placeholder="Description"
          value={description}
          onChange={event => setDescription(event.target.value)}
        />
        <input
          className="my-4 px-4 py-2 bg-purple-700 text-white rounded-lg"
          type="submit"
          onClick={onSubmitPostProduct}
          value="Post"
        />
      </form>
    </main>
  );
}
