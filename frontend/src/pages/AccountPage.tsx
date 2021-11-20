import { useEffect, useState } from "react";
import { Account } from "../types";

export default function AccountPage() {
  const fakeAccount: Account = { id: 1, name: "Nikolas", email: "nikolas@uwo.ca", password: "123" };
  const [email, setEmail] = useState(fakeAccount.email);
  const [name, setName] = useState(fakeAccount.name);
  const [isUpdateButtonDisabled, setUpdateButtonDisabled] = useState(true);

  useEffect(() => {
    // Activate update button only once name or email are different from their original values.
    if (name != fakeAccount.name || email != fakeAccount.email ) {
      setUpdateButtonDisabled(false);
    } else {
      setUpdateButtonDisabled(true);
    }
  }, [name, email]);

  function onSubmitUpdateAccount(event: any) {
    event.preventDefault();
    if (!name || !email) {
      alert("All fields are required.");
    } else {
      alert("Account updated.");
    }
  }

  return (
    <main className="container mx-auto px-4 flex flex-col items-center justify-center h-screen">
      <h1 className="text-4xl font-medium text-center">Account Details</h1>
      <p className="text-center">Current Balance: $100.00</p>
      <form className="flex flex-col items-center">
        <input
          type="text"
          placeholder={email}
          onChange={event => setEmail(event.target.value)}
        />
        <input
          type="text"
          placeholder={name}
          onChange={event => setName(event.target.value)}
        />
        <input
          className="my-4 px-4 py-2 bg-purple-700 text-white rounded-lg"
          type="submit"
          onClick={onSubmitUpdateAccount}
          value="Update"
          disabled={isUpdateButtonDisabled}
        />
      </form>
    </main>
  );
}
