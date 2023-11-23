/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import com.example.cupcake.model.OrderViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cupcake.adapter.FlavorAdapter
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.Flavor

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        setHasOptionsMenu(true)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(sharedViewModel.getFlavorQuantityList().isEmpty()) {
            for (flavor in requireContext().resources.getStringArray(R.array.flavors)) {
                sharedViewModel.setFlavorQuantityList(Flavor(flavor))
            }
        }

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            flavorFragment = this@FlavorFragment
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@FlavorFragment.context)
                adapter = FlavorAdapter(sharedViewModel)
          //      getChildAt(2).findViewById<>(R.id.plusButton)
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.getFlavorQuantityList().clear()
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    /**
     * Navigate to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
        if(sharedViewModel.getFlavorQuantityList()[2].quantity > 0){
            sharedViewModel.setDate(sharedViewModel.dateOptions[1])
        }
        else{
            sharedViewModel.setDate(sharedViewModel.dateOptions[0])
        }
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // handle the up button here
        sharedViewModel.getFlavorQuantityList().clear()

        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}